package com.example.catalist.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Breed(
    val id: String,
    val name: String,
    @SerialName("alt_names")
    val altNames: String? = null,
    val description: String,
    val temperament: String,
    val origin: String? = null,
    val country_codes: String? = null,
    val country_code: String? = null,
    val life_span: String? = null,
    val weight: Weight? = null,
    val rare: Int? = null,
    @SerialName("wikipedia_url")
    val wikipediaUrl: String? = null,

    // osobine
    @SerialName("adaptability")
    val adaptability: Int? = null,
    @SerialName("affection_level")
    val affectionLevel: Int? = null,
    @SerialName("child_friendly")
    val childFriendly: Int? = null,
    @SerialName("dog_friendly")
    val dogFriendly: Int? = null,
    @SerialName("energy_level")
    val energyLevel: Int? = null,
    val grooming: Int? = null,
    @SerialName("health_issues")
    val healthIssues: Int? = null,
    val intelligence: Int? = null,
    @SerialName("shedding_level")
    val sheddingLevel: Int? = null,
    @SerialName("social_needs")
    val socialNeeds: Int? = null,
    @SerialName("stranger_friendly")
    val strangerFriendly: Int? = null,
    val vocalisation: Int? = null,

    //slike su u posebnom pozivu, ovo samo za referencu
    @SerialName("reference_image_id")
    val imageId: String? = null
)

@Serializable
data class Weight(
    val imperial: String? = null,
    val metric: String? = null
)
