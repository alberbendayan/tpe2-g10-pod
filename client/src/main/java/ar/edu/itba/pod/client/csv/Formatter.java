package ar.edu.itba.pod.client.csv;

import models.Ticket;

public interface Formatter {

    Ticket formatTicket(String line);
}