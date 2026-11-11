package com.nsp.socialmediaapi.model.request.User;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserProfileInfoRequest {
    @Size(max = 255)
    private String newUserDesc;

}
