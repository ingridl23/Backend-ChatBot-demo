package com.chat.demo.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String userName;
    private String lastName;
    private String email;
    private Boolean enabled;

    private Long organizationId;
    private String organizationName;

    private Long areaId;
    private String areaName;

    private List<String> roles;

    private Date createdAt;
    private Date updatedAt;
}
