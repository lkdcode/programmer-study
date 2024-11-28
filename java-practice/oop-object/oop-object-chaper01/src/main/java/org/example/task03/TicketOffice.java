package org.example.task03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TicketOffice {
    private Long amount;
    private List<Ticket> tickets = new ArrayList<>();

    public TicketOffice(Long amount, Ticket... tickets) {
        this.amount = amount;
        this.tickets.addAll(Arrays.asList(tickets));
    }

    public void sellToTicket(Audience audience) {
        plusAmount(audience.buy(getTicket()));
    }

    private Ticket getTicket() {
        return tickets.get(0);
    }

    private void plusAmount(Long amount) {
        this.amount += amount;
    }
}