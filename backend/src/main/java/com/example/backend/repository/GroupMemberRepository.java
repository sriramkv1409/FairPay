package com.example.backend.repository;

import com.example.backend.entity.Group;
import com.example.backend.entity.GroupMember;
import com.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember,Long> {

    boolean existsByGroupAndUser(Group group, User user);

    List<GroupMember> findByGroup(Group group);

    Optional<GroupMember> findByUserAndUser(Group group,User user);

}
