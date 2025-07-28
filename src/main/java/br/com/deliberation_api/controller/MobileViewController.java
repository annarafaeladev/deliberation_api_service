package br.com.deliberation_api.controller;


import br.com.deliberation_api.application.view.dto.structure.ViewTemplateResponseDTO;
import br.com.deliberation_api.interfaces.service.MobileViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/mobile")
@RequiredArgsConstructor
public class MobileViewController {

    @Autowired
    private final MobileViewService mobileViewService;

    @GetMapping("/pages/{pageId}")
    public ResponseEntity<ViewTemplateResponseDTO> getPage(@PathVariable String pageId) {
        return ResponseEntity.ok(mobileViewService.getPage(pageId));
    }

    @GetMapping("/pages/{pageId}/topics")
    public ResponseEntity<ViewTemplateResponseDTO> getTopics(@PathVariable String pageId) {
        return ResponseEntity.ok(mobileViewService.getTopics(pageId));
    }

    @GetMapping("/pages/{pageId}/topics/{topicId}")
    public ResponseEntity<ViewTemplateResponseDTO> getPageOptions(@PathVariable String pageId, @PathVariable String topicId) {
        return ResponseEntity.ok(mobileViewService.getPageOptions(pageId, topicId));
    }

    @GetMapping("/pages/{pageId}/topics/{topicId}/options/{optionId}")
    public ResponseEntity<ViewTemplateResponseDTO> getPageOption(@PathVariable String pageId,@PathVariable String topicId, @PathVariable String optionId) {
        return ResponseEntity.ok(mobileViewService.getPageOptionByOptionId(pageId, topicId, optionId));
    }

    @GetMapping("/pages/{pageId}/profile/{associateId}")
    public ResponseEntity<ViewTemplateResponseDTO> getProfileDetails(@PathVariable String pageId, @PathVariable String associateId) {
        return ResponseEntity.ok(mobileViewService.getProfilePage(pageId, associateId));
    }
}
