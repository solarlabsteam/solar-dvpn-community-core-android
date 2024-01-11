package co.sentinel.dvpn.hub.mapper

import android.util.Base64
import co.sentinel.dvpn.domain.core.extension.bytesToUnsignedShort
import co.sentinel.dvpn.domain.core.functional.Mapper
import co.sentinel.dvpn.domain.features.hub.model.VpnProfile

object ProfileMapper : Mapper<String, VpnProfile> {

    override fun map(obj: String): VpnProfile =
        Base64.decode(obj, Base64.DEFAULT).let { bytes ->
            val address =
                "${bytes[0].toUByte()}.${bytes[1].toUByte()}.${bytes[2].toUByte()}.${bytes[3].toUByte()}/32"
            val port = bytesToUnsignedShort(bytes[24], bytes[25], bigEndian = true)
            val host =
                "${bytes[20].toUByte()}.${bytes[21].toUByte()}.${bytes[22].toUByte()}.${bytes[23].toUByte()}"
            val peerEndpoint = "$host:$port"
            val pubKeyBytes = bytes.copyOfRange(26, 58)

            VpnProfile(
                address = address,
                host = host,
                listenPort = "$port",
                peerEndpoint = peerEndpoint,
                peerPubKeyBytes = pubKeyBytes,
            )
        }
}
