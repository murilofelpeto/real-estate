package com.felpeto.realestate.jpa.property.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@ToString
@Table(name = "leisure_item", indexes = {
    @Index(name = "idx_leisure_item_entity", columnList = "leisure_item_id, uuid, item")})
@NoArgsConstructor
@AllArgsConstructor
public class LeisureItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "leisure_item_id", nullable = false)
  private Long leisureItemId;

  @Column(columnDefinition = "BINARY(16)", nullable = false, unique = true)
  private UUID uuid;

  @Column(name = "item", unique = true, nullable = false)
  private String item;

  @PrePersist
  public void prePersist() {
    this.uuid = UUID.randomUUID();

  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final LeisureItemEntity that = (LeisureItemEntity) o;
    return Objects.equals(uuid, that.uuid) && Objects.equals(item, that.item);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, item);
  }
}
