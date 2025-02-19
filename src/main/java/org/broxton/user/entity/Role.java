package org.broxton.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.broxton.user.models.UserRoles;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private UserRoles name;
}
