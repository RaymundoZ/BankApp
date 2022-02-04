package com.raymundo.bankapp.ui;

import com.raymundo.bankapp.dto.LoanOfferDto;
import com.raymundo.bankapp.dto.PaymentScheduleDto;
import com.raymundo.bankapp.events.CancelEvent;
import com.raymundo.bankapp.events.CreateEvent;
import com.raymundo.bankapp.events.DeleteEvent;
import com.raymundo.bankapp.events.EditEvent;
import com.raymundo.bankapp.services.ClientService;
import com.raymundo.bankapp.services.CreditService;
import com.raymundo.bankapp.services.LoanOfferService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;

@Route(value = "offers", layout = MainLayout.class)
public class LoanOfferLayout extends VerticalLayout {

    private final LoanOfferService service;
    private final Grid<LoanOfferDto> grid;
    private final HorizontalLayout toolbar;
    private final Button createButton;
    private final LoanOfferDialog dialog;

    @Autowired
    public LoanOfferLayout(LoanOfferService service, ClientService clientService, CreditService creditService) {
        this.service = service;
        this.grid = new Grid<>();
        this.toolbar = new HorizontalLayout();
        this.createButton = new Button("Create new loan") {{
            addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        }};
        this.dialog = new LoanOfferDialog(clientService, creditService);
        setSizeFull();
        configToolbar();
        configGrid();
        configDialog();
        refreshData();
    }

    private void configToolbar() {
        createButton.addClickListener(event -> dialog.open());
        toolbar.add(createButton);
        add(toolbar);
    }

    private void configGrid() {
        grid.addColumn(offer -> offer.getClient().getName()).setHeader("Name");
        grid.addColumn(offer -> offer.getClient().getSurname()).setHeader("Surname");
        grid.addColumn(offer -> offer.getClient().getPatronymic()).setHeader("Patronymic");
        grid.addColumn(LoanOfferDto::getCreditAmount).setHeader("Credit sum");
        grid.addColumn(offer -> offer.getCredit().getInterestRate()).setHeader("Interest rate");
        grid.addColumn(offer -> String.format("%.1f", offer.getPaymentSchedules().stream().mapToDouble(PaymentScheduleDto::getPaymentAmount).sum())).setHeader("Total loan amount");
        grid.setDetailsVisibleOnClick(false);
        grid.setItemDetailsRenderer(new ComponentRenderer<>(loanOfferDto -> {
            VerticalLayout verticalLayout = new VerticalLayout();
            Grid<PaymentScheduleDto> scheduleGrid = new Grid<>();
            scheduleGrid.addComponentColumn(schedule -> new Label(new SimpleDateFormat("dd.MM.yyyy").format(schedule.getPaymentDate()))).setHeader("Date");
            scheduleGrid.addColumn(schedule -> String.format("%.1f", schedule.getPaymentAmount())).setHeader("Payment amount");
            scheduleGrid.addColumn(schedule -> String.format("%.1f", schedule.getLoanRepaymentAmount())).setHeader("Loan repayment amount");
            scheduleGrid.addColumn(schedule -> String.format("%.1f", schedule.getInterestPaymentAmount())).setHeader("Interest payment amount");
            scheduleGrid.setItems(loanOfferDto.getPaymentSchedules());
            scheduleGrid.setSelectionMode(Grid.SelectionMode.NONE);
            verticalLayout.add(new H3("Payment schedule"), scheduleGrid);
            return verticalLayout;
        }));
        grid.addComponentColumn(offer -> new Button("Show") {{
            addThemeVariants(ButtonVariant.LUMO_SMALL);
            addClickListener(event -> grid.setDetailsVisible(offer, !grid.isDetailsVisible(offer)));
        }});
        grid.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                dialog.open(event.getValue());
            }
        });
        add(grid);
    }

    private void configDialog() {
        dialog.setListener(CreateEvent.class, event -> {
            service.create((LoanOfferDto) event.getDto());
            refreshData();
            dialog.close();
            grid.asSingleSelect().clear();
            Notification.show("Loan offer was created!");
        });
        dialog.setListener(CancelEvent.class, event -> {
            dialog.close();
            grid.asSingleSelect().clear();
        });
        dialog.setListener(EditEvent.class, event -> {
            service.update((LoanOfferDto) event.getDto());
            refreshData();
            dialog.close();
            grid.asSingleSelect().clear();
            Notification.show("Loan offer was edited!");
        });
        dialog.setListener(DeleteEvent.class, event -> {
            service.delete(((LoanOfferDto) event.getDto()).getUuid());
            refreshData();
            dialog.close();
            grid.asSingleSelect().clear();
            Notification.show("Loan offer was deleted!");
        });
    }

    private void refreshData() {
        grid.setItems(service.getAll());
    }

}
