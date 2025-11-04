//package dev.lkdcode
//
//import org.jooq.codegen.DefaultGeneratorStrategy
//import org.jooq.codegen.GeneratorStrategy
//import org.jooq.meta.Definition
//import org.jooq.meta.TableDefinition
//
//
//import org.jooq.codegen.DefaultGeneratorStrategy
//
//class PrefixGeneratorStrategy : DefaultGeneratorStrategy() {
//    override fun getJavaClassName(definition: Definition, mode: GeneratorStrategy.Mode): String =
//        if (mode == GeneratorStrategy.Mode.DEFAULT || mode == GeneratorStrategy.Mode.RECORD) {
//            "J" + super.getJavaClassName(definition, mode)
//        } else super.getJavaClassName(definition, mode)
//
//
//    override fun getJavaIdentifier(definition: Definition?): String =
//        when (definition) {
//            is TableDefinition -> "J" + super.getJavaIdentifier(definition)
//
//            else -> super.getJavaIdentifier(definition)
//        }
//}