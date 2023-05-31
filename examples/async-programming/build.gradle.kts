repositories {
	mavenCentral()
}

dependencies {
	// reactor
	implementation("io.projectreactor:reactor-core:_")

	// rxjava
	implementation("io.reactivex.rxjava3:rxjava:_")

	// mutiny
	implementation("io.smallrye.reactive:mutiny:2.1.0")
}
