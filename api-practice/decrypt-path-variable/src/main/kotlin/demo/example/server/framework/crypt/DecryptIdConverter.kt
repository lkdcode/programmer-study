package demo.example.server.framework.crypt

import org.springframework.core.convert.TypeDescriptor
import org.springframework.core.convert.converter.ConditionalGenericConverter
import org.springframework.core.convert.converter.GenericConverter
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair
import org.springframework.stereotype.Component

@Component
class DecryptIdConverter(
    private val cryptoService: CryptoService
) : ConditionalGenericConverter {

    override fun getConvertibleTypes(): MutableSet<GenericConverter.ConvertiblePair>? =
        mutableSetOf(
            ConvertiblePair(String::class.java, java.lang.Long::class.java),
            ConvertiblePair(String::class.java, java.lang.Long.TYPE)
        )

    override fun convert(source: Any?, sourceType: TypeDescriptor, targetType: TypeDescriptor): Any? =
        cryptoService.decryptId(source as String)

    override fun matches(sourceType: TypeDescriptor, targetType: TypeDescriptor): Boolean =
        targetType.hasAnnotation(DecryptId::class.java)

}