package mk.finki.ukim.mk.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.finki.ukim.mk.lab.model.Event;
import mk.finki.ukim.mk.lab.service.EventService;
import mk.finki.ukim.mk.lab.service.SavedBookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/eventBookingController")
public class EventBookingController {
    private final EventService eventService;
    private final SavedBookingService savedBookingService;

    public EventBookingController(EventService eventService, SavedBookingService savedBookingService) {
        this.eventService = eventService;
        this.savedBookingService = savedBookingService;
    }

    @GetMapping
    public String getBookingConfirmationPage() {
        return "bookingConfirmation";
    }

    @PostMapping
    public String addReservation (HttpServletRequest req, Model model) {
        try {
            String eventName = req.getParameter("rad");
            Integer numOfTickets = Integer.valueOf(req.getParameter("numTickets"));
            String hostName = req.getParameter("hostName");

            this.savedBookingService.save(hostName, eventName, numOfTickets);
            return "bookingConfirmation";
        } catch (RuntimeException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "listEvents";
        }
    }
}
