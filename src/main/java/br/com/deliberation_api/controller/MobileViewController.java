package br.com.deliberation_api.controller;

import br.com.deliberation_api.application.view.dto.response.ViewMobileProfileResponseDTO;
import br.com.deliberation_api.application.view.dto.response.ViewMobileTopicFormResponseDTO;
import br.com.deliberation_api.application.view.dto.response.ViewMobileTopicOptionsResponseDTO;
import br.com.deliberation_api.application.service.MobileViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/view")
@RequiredArgsConstructor
public class MobileViewController {

    @Autowired
    private final MobileViewService mobileViewService;

    @GetMapping("/topics/form")
    public ResponseEntity<ViewMobileTopicFormResponseDTO> getPageCreateTopic() {
        return ResponseEntity.ok(mobileViewService.getPageCreateTopic());
    }

    @GetMapping("/topics")
    public ResponseEntity<ViewMobileTopicOptionsResponseDTO> getTopics() {
        return ResponseEntity.ok(mobileViewService.getTopics());
    }

    @GetMapping("/topics/{topicId}/options")
    public ResponseEntity<ViewMobileTopicOptionsResponseDTO> getPageOptions(@PathVariable String topicId) {
        return ResponseEntity.ok(mobileViewService.getPageOptions(topicId));
    }

    @GetMapping("/topics/{topicId}/options/{optionId}")
    public ResponseEntity<ViewMobileTopicOptionsResponseDTO> getPageOption(@PathVariable String topicId, @PathVariable String optionId) {
        return ResponseEntity.ok(mobileViewService.getPageOptionByOptionId(topicId, optionId));
    }

    @GetMapping("/profile/{associateId}")
    public ResponseEntity<ViewMobileProfileResponseDTO> getProfileDetails(@PathVariable String associateId) {
        return ResponseEntity.ok(mobileViewService.getProfilePage(associateId));
    }
}
