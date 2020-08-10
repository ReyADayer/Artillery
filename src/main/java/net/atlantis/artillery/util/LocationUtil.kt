package net.atlantis.artillery.util

import org.bukkit.Location
import org.bukkit.util.Vector

object LocationUtil {

    /**
     * 目線のベクトルを標準とした座標系へ変換する
     * @param x        基底座標でのx
     * @param y        基底座標でのy
     * @param z        基底座標でのz
     * @return 座標変換されたLocation
     */
    fun transform(currentLocation: Location, x: Double, y: Double, z: Double): Location {
        val location = currentLocation.clone()
        var rate = 1.0
        val vector: Vector = location.direction
        if (vector.x > 0) {
            rate = -1.0
        }
        val targetAngle = Math.atan(vector.z / vector.x) + Math.PI / 2
        val targetAngle2 = rate * Math.atan(vector.y / Math.sqrt(Math.pow(vector.x, 2.0) + Math.pow(vector.z, 2.0)))

        val z1 = z * Math.cos(targetAngle2) - y * Math.sin(targetAngle2)
        val y1 = z * Math.sin(targetAngle2) + y * Math.cos(targetAngle2)

        val x1 = x * Math.cos(targetAngle) - z1 * Math.sin(targetAngle)
        val z2 = x * Math.sin(targetAngle) + z1 * Math.cos(targetAngle)

        // 位置の調整
        return location.add(x1, y1, z2)
    }
}