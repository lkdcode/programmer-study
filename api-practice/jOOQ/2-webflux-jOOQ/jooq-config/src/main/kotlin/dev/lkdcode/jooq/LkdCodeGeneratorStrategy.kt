package dev.lkdcode.jooq

import org.jooq.codegen.DefaultGeneratorStrategy
import org.jooq.codegen.GeneratorStrategy
import org.jooq.meta.Definition
import org.jooq.meta.TableDefinition

class LkdCodeGeneratorStrategy : DefaultGeneratorStrategy() {

    override fun getJavaClassName(definition: Definition, mode: GeneratorStrategy.Mode): String =
        if (mode == GeneratorStrategy.Mode.DEFAULT || mode == GeneratorStrategy.Mode.RECORD) {
            DEFAULT_PREFIX + super.getJavaClassName(definition, mode)
        } else super.getJavaClassName(definition, mode)

    override fun getJavaIdentifier(definition: Definition?): String =
        when (definition) {
            is TableDefinition -> DEFAULT_PREFIX + super.getJavaIdentifier(definition)
            else -> super.getJavaIdentifier(definition)
        }

    companion object {
        private const val DEFAULT_PREFIX = "J"
    }
}
