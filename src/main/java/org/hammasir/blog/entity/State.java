package org.hammasir.blog.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.MultiPolygon;

@Getter
@Setter
@Entity
@Table(name = "iran_states")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iran_states_id_gen")
    @SequenceGenerator(name = "iran_states_id_gen", sequenceName = "ir_gid_seq", allocationSize = 1)
    @Column(name = "gid", nullable = false)
   Integer id;

    @Column(name = "name", length = 27)
    String name;

    @Column(name = "geom", columnDefinition = "geometry")
    MultiPolygon geom;

}