package br.com.deliberation_api.infrastructure.exception;

import br.com.deliberation_api.shared.exception.SessionException;
import br.com.deliberation_api.shared.exception.TopicNotFoundException;
import br.com.deliberation_api.shared.exception.VoteException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestExceptionController {

    @PostMapping("/vote-error")
    public void voteError() {
        throw new VoteException("Voting session has expired");
    }

    @PostMapping("/session-error")
    public void sessionError() {
        throw new SessionException("Session expired");
    }

    @PostMapping("/topic-not-found")
    public void topicNotFound() {
        throw new TopicNotFoundException("Topic not found");
    }

    @PostMapping("/generic-error")
    public void genericError() {
        throw new RuntimeException("Something went wrong");
    }

    @PostMapping("/validation-error")
    public void validationErrorDTO(@Valid @RequestBody SomeDTO dto) {}
}

class SomeDTO {
    @NotNull(message = "field cannot be null")
    private String field;
}
