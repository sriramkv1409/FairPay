package com.example.backend.controller;

import com.example.backend.dto.CreateGroupRequest;
import com.example.backend.entity.Group;
import com.example.backend.entity.GroupMember;
import com.example.backend.repository.GroupRepository;
import com.example.backend.service.GroupService;
import com.example.backend.service.QrCodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final QrCodeService qrCodeService;

    @PostMapping("/create/{creatorId}")
    public ResponseEntity<Group> createGroup(
            @PathVariable Long creatorId,
            @Valid @RequestBody CreateGroupRequest request) {

        return ResponseEntity.ok(groupService.createGroup(request, creatorId));
    }

    @PostMapping("/{groupId}/join/{userId}")
    public ResponseEntity<String> joinGroup(
            @PathVariable Long groupId,
            @PathVariable Long userId) {

        groupService.joinGroup(groupId, userId);
        return ResponseEntity.ok("Joined group successfully");
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<GroupMember>> getMembers(
            @PathVariable Long groupId) {

        return ResponseEntity.ok(groupService.getGroupMembers(groupId));
    }

    @GetMapping("/{groupId}/qr")
    public ResponseEntity<String> getGroupQr(@PathVariable Long groupId) {

        String qrText = "fairpay://join?groupId=" + groupId;
        String qrBase64 = qrCodeService.generateQrCode(qrText);

        return ResponseEntity.ok(qrBase64);
    }
}
