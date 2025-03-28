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
@Table(name = "products")
public class Products {

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

    @Column(name = "active_from")
    @Temporal(TemporalType.DATE)
    public Date activeFrom;

    @Column(name = "active_to")
    @Temporal(TemporalType.DATE)
    public Date activeTo;

    @Column(name = "base_image")
    public String baseImage;

    @Column(name = "customer_discount")
    public Double customerDiscount;

    @Lob
    @Column(name = "description",columnDefinition = "LONGTEXT")
    public String description;

    @Column(name = "employee_discount")
    public Double employeeDiscount;

    @Basic(optional = false)
    @Column(name = "enabled")
    public boolean enabled;

    @Basic(optional = false)
    @Column(name = "enabled_incentive")
    public boolean enabledIncentive;

    @Column(name = "incentive")
    public Boolean incentive;

    @Column(name = "level_incentive")
    public Double levelIncentive;

    @Basic(optional = false)
    @Column(name = "loaded_images")
    public boolean loadedImages;

    @Column(name = "parent_product")
    public String parentProduct;

    @Column(name = "price")
    public Double price;

    @Column(name = "price_pum")
    public Double pricePum;

    @Basic(optional = false)
    @Column(name = "product_type")
    public int productType;

    @Basic(optional = false)
    @Column(name = "require_index")
    public boolean requireIndex;

    @Column(name = "search_terms")
    public String searchTerms;

    @Lob
    @Column(name = "short_description",columnDefinition = "LONGTEXT")
    public String shortDescription;

    @Column(name = "special_price")
    public Double specialPrice;

    @Column(name = "sync_images")
    public Boolean syncImages;

    @Basic(optional = false)
    @Column(name = "tax_percent")
    public double taxPercent;

    @Column(name = "unit_pum")
    public String unitPum;

    @Basic(optional = false)
    @Column(name = "visibility")
    public String visibility;

    @Basic(optional = false)
    @Column(name = "excluded")
    public boolean excluded;

    @JoinTable(name = "product_categories", joinColumns = {
        @JoinColumn(name = "categories_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "products_id", referencedColumnName = "id")})
    @ManyToMany
    public Collection<Categories> categoriesCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "products")
    public Collection<ProductIndices> productIndicesCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productsId")
    public Collection<Images> imagesCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "products")
    public Collection<ProductAttributes> productAttributesCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productsId")
    public Collection<Prices> pricesCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "products")
    public Collection<ConfigurableProductOptions> configurableProductOptionsCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "products")
    public Collection<ConfigurableProductLinks> configurableProductLinksCollection;
    
    @Column(name = "substitute_code")
    public String substituteCode;
    
    @Column(name = "substitute_priority")
    public int substitutePriority;
    
    @Column(name = "substitute_description")
    public String substituteDescription;
}
