/*
 * Licencia de Software para Croydon Colombia
 *
 * Copyright (c) 2024 Croydon Colombia
 *
 * Este programa es software propietario de Croydon Colombia.
 * No está permitida su distribución, copia, modificación o uso no autorizado.
 * Cualquier intento de reproducción sin consentimiento será considerado una violación de esta licencia.
 *
 * CROYDON COLOMBIA NO OTORGA GARANTÍAS EXPRESAS O IMPLÍCITAS SOBRE ESTE SOFTWARE.
 * El uso de este software implica la aceptación de los términos y condiciones establecidos.
 *
 */
package com.croydon.model.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Collection;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Edwin Torres - Email: edwin.torres@croydon.com.co
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "catalog_products")
public class CatalogProducts {
    
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    public String id;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    public Date updatedAt;

    @Lob
    @Column(name = "meta_description",columnDefinition = "LONGTEXT")
    public String metaDescription;

    @Column(name = "meta_keywords")
    public String metaKeywords;

    @Column(name = "meta_title")
    public String metaTitle;

    @Basic(optional = false)
    @Column(name = "name")
    public String name;

    @Column(name = "url_key")
    public String urlKey;

    @Basic(optional = false)
    @Column(name = "active")
    public boolean active;

    @Column(name = "base_image")
    public String baseImage;

    @Lob
    @Column(name = "description", columnDefinition = "LONGTEXT")
    public String description;

    @Column(name = "final_max_price")
    public Double finalMaxPrice;

    @Column(name = "final_min_price")
    public Double finalMinPrice;

    @Column(name = "max_price")
    public Double maxPrice;

    @Column(name = "max_special_price")
    public Double maxSpecialPrice;

    @Column(name = "min_price")
    public Double minPrice;

    @Column(name = "min_special_price")
    public Double minSpecialPrice;

    @Lob
    @Column(name = "search_terms",columnDefinition = "LONGTEXT")
    public String searchTerms;

    @Lob
    @Column(name = "search_terms_string",columnDefinition = "LONGTEXT")
    public String searchTermsString;

    @Lob
    @Column(name = "short_description", columnDefinition = "LONGTEXT")
    public String shortDescription;

    @Column(name = "tax_percent")
    public Double taxPercent;

    @Basic(optional = false)
    @Column(name = "visibility")
    public String visibility;

    @Column(name = "level_incentive")
    public Double levelIncentive;

    @JoinTable(name = "catalog_product_categories", joinColumns = {
        @JoinColumn(name = "categories_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "catalog_products_id", referencedColumnName = "id")})
    @ManyToMany
    public Collection<Categories> categoriesCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catalogProductsId")
    public Collection<CatalogProductAttributes> catalogProductAttributesCollection;
    
}
