package dev.lkdcode.cache.resolver

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator

class KotlinTypeResolver(
    defaultTyping: ObjectMapper.DefaultTyping,
    polymorphicTypeValidator: PolymorphicTypeValidator
) : ObjectMapper.DefaultTypeResolverBuilder(defaultTyping, polymorphicTypeValidator) {

    override fun useForType(javaType: JavaType): Boolean {
        if (javaType.isPrimitive || javaType.rawClass == String::class.java) return false
        if (super.useForType(javaType)) return true

        return true
    }
}