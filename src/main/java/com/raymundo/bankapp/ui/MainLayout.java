package com.raymundo.bankapp.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@PageTitle(value = "Bank")
@Route(value = "")
public class MainLayout extends AppLayout {

    public MainLayout() {
        H3 logo = new H3("Bank App");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.add(logo);
        header.setWidthFull();
        addToNavbar(header);


        RouterLink clients = new RouterLink("Clients", ClientLayout.class);
        RouterLink credits = new RouterLink("Credits", CreditLayout.class);
        RouterLink banks = new RouterLink("Banks", BankLayout.class);
        RouterLink offers = new RouterLink("Offers", LoanOfferLayout.class);
        addToDrawer(new VerticalLayout(clients, credits, banks, offers) {{
            setAlignItems(Alignment.CENTER);
        }});
    }

}
