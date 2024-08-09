package vn.datk.jobhunter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.datk.jobhunter.domain.Subscriber;
import vn.datk.jobhunter.service.SubscriberService;
import vn.datk.jobhunter.util.annotation.ApiMessage;
import vn.datk.jobhunter.util.error.IdInvalidException;
import vn.datk.jobhunter.util.security.SecurityUtils;

@RestController
@RequestMapping("/api/v1")
public class SubscriberController {
    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping("/subscribers")
    @ApiMessage("Create a subscriber")
    public ResponseEntity<Subscriber> create(@Valid @RequestBody Subscriber sub) throws IdInvalidException {
        // check email
        boolean isExist = this.subscriberService.isExistsByEmail(sub.getEmail());
        if (isExist == true) {
            throw new IdInvalidException("Email " + sub.getEmail() + " already exists");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.subscriberService.create(sub));
    }

    @PutMapping("/subscribers")
    @ApiMessage("Update a subscriber")
    public ResponseEntity<Subscriber> update(@RequestBody Subscriber subsRequest) throws IdInvalidException {
        // check id
        Subscriber subsDB = this.subscriberService.findById(subsRequest.getId());
        if (subsDB == null) {
            throw new IdInvalidException("Id " + subsRequest.getId() + " is not valid");
        }
        return ResponseEntity.ok().body(this.subscriberService.update(subsDB, subsRequest));
    }

    @PostMapping("/subscribers/skills")
    @ApiMessage("Get subscriber's skill")
    public ResponseEntity<Subscriber> getSubcribersSkill() throws IdInvalidException {
        String email = SecurityUtils.getCurrentUserLogin().isPresent()
                ? SecurityUtils.getCurrentUserLogin().get()
                : "";
        return ResponseEntity.status(HttpStatus.OK).body(this.subscriberService.findByEmail(email));
    }
}
