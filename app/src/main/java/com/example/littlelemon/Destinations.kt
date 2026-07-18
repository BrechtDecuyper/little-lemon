package com.example.littlelemon

interface Destinations {
    val route: String
}

object DestHome: Destinations {
    override val route = "Home"
}
object DestProfile: Destinations {
    override val route = "Profile"
}
object DestOnboarding: Destinations {
    override val route = "Onboarding"
}