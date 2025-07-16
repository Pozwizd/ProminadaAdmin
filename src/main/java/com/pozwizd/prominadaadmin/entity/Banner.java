package com.pozwizd.prominadaadmin.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    Boolean status;



    @OneToMany(mappedBy = "banner", fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    List<ImageBanner> imageBanners = new ArrayList<>();

    public void addImageBanner(ImageBanner imageBanner) {
        imageBanners.add(imageBanner);
        imageBanner.setBanner(this);
    }

    public void removeImageBanner(ImageBanner imageBanner) {
        imageBanners.remove(imageBanner);
        imageBanner.setBanner(null);
    }

    public void setImageBanners(List<ImageBanner> imageBanners) {
        this.imageBanners.clear();
        if (imageBanners != null) {
            imageBanners.forEach(this::addImageBanner);
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Banner banner = (Banner) o;
        return getId() != null && Objects.equals(getId(), banner.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}