class KalmanFilter {
    private var x = DoubleArray(2) // State [latitude, longitude]
    private var P = Array(2) { DoubleArray(2) } // Covariance matrix
    private var Q = Array(2) { DoubleArray(2) } // Process noise covariance
    private var R = Array(2) { DoubleArray(2) } // Measurement noise covariance
    private val H = arrayOf(doubleArrayOf(1.0, 0.0), doubleArrayOf(0.0, 1.0)) // Measurement matrix

    init {
        // Initialize state estimate
        x[0] = 0.0 // initial latitude
        x[1] = 0.0 // initial longitude

        // Initialize covariance matrix
        P[0][0] = 1.0 // initial variance in latitude
        P[0][1] = 0.0
        P[1][0] = 0.0
        P[1][1] = 1.0 // initial variance in longitude

        // Process noise covariance (increase these values)
        Q[0][0] = 0.1  // Increased for more trust in the measurements
        Q[0][1] = 0.0
        Q[1][0] = 0.0
        Q[1][1] = 0.1

        // Measurement noise covariance (decrease these values)
        R[0][0] = 0.01 // Decreased for more trust in measurements
        R[0][1] = 0.0
        R[1][0] = 0.0
        R[1][1] = 0.01
    }

    fun predict() {
        // Predict the next state (no control input for now)
        P[0][0] += Q[0][0]
        P[1][1] += Q[1][1]
    }

    fun update(measurement: DoubleArray) {
        // Calculate the Kalman Gain
        val y = DoubleArray(2) // Innovation
        y[0] = measurement[0] - (H[0][0] * x[0] + H[0][1] * x[1]) // latitude
        y[1] = measurement[1] - (H[1][0] * x[0] + H[1][1] * x[1]) // longitude

        val S = Array(2) { DoubleArray(2) }
        for (i in 0..1) {
            for (j in 0..1) {
                S[i][j] = H[i][0] * P[0][j] + H[i][1] * P[1][j] + R[i][j]
            }
        }

        // Kalman Gain
        val K = Array(2) { DoubleArray(2) }
        for (i in 0..1) {
            for (j in 0..1) {
                K[i][j] = P[i][0] * H[0][j] + P[i][1] * H[1][j]
                K[i][j] /= S[i][i] // Simplified for this case
            }
        }

        // Update the state estimate
        for (i in 0..1) {
            x[i] += K[i][0] * y[0] + K[i][1] * y[1]
        }

        // Update the covariance matrix
        val I = Array(2) { DoubleArray(2) }
        for (i in 0..1) I[i][i] = 1.0

        val KHT = Array(2) { DoubleArray(2) }
        for (i in 0..1) {
            for (j in 0..1) {
                KHT[i][j] = K[i][0] * H[0][j] + K[i][1] * H[1][j]
            }
        }

        for (i in 0..1) {
            for (j in 0..1) {
                P[i][j] = (I[i][j] - KHT[i][j]) * P[i][j]
            }
        }
    }

    fun getCurrentEstimate(): DoubleArray {
        return x
    }
}
