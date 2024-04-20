package kg.fuankan.tezcargo.annotations

import java.lang.annotation.Inherited

@Inherited
@Target(AnnotationTarget.CLASS, AnnotationTarget.CONSTRUCTOR)
@Retention(AnnotationRetention.RUNTIME)
annotation class UsesSharedViewModel
