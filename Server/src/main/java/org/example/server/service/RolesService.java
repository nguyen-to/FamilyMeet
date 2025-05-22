package org.example.server.service;

import org.example.server.entity.Roles;
import org.example.server.utill.RolesEnum;

public interface RolesService {
    public Roles getRoles(RolesEnum role);
}
