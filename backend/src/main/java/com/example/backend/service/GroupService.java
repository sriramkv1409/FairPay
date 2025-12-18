package com.example.backend.service;

import com.example.backend.dto.CreateGroupRequest;
import com.example.backend.entity.Group;
import com.example.backend.entity.GroupMember;
import com.example.backend.entity.User;
import com.example.backend.repository.GroupMemberRepository;
import com.example.backend.repository.GroupRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;

    public Group createGroup(CreateGroupRequest request,Long creatorId){
        User creator = userRepository.findById(creatorId)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Group group = Group.builder()
                .name(request.getGroupName())
                .upiId(request.getUpiId())
                .createdBy(creator)
                .totalAmount(0.0)
                .build();

        Group savedGroup = groupRepository.save(group);

        GroupMember member = GroupMember.builder()
                .group(savedGroup)
                .user(creator)
                .build();

        groupMemberRepository.save(member);
        return savedGroup;
    }

    public void joinGroup(Long groupId,Long userId){
          Group group = groupRepository.findById(groupId)
                  .orElseThrow(()->new RuntimeException("Group not found"));

          User user = userRepository.findById(userId)
                  .orElseThrow(()->new RuntimeException("User not found"));

          if(groupMemberRepository.existsByGroupAndUser(group,user)){
              throw new RuntimeException("User already in Group");
          }

          GroupMember member = GroupMember.builder()
                  .group(group)
                  .user(user)
                  .build();

          groupMemberRepository.save(member);
    }

    public List<GroupMember> getGroupMembers(Long groupId){
        Group group = groupRepository.findById(groupId)
                .orElseThrow(()->new RuntimeException("Group not found"));

        return groupMemberRepository.findByGroup(group);
    }
}
