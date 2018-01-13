(ns web.utils
  (:require [goog.crypt :as gcrypt]
            [goog.crypt.Md5 :as Md5]))

(defn string->bytes [s]
  (gcrypt/stringToUtf8ByteArray s))  ;; must be utf8 byte array

(defn bytes->hex
  "convert bytes to hex"
  [bytes-in]
  (gcrypt/byteArrayToHex bytes-in))

(defn hash-bytes [digester bytes-in]
  (do
    (.update digester bytes-in)
    (.digest digester)))

(defn md5-
  "convert bytes to md5 bytes"
  [bytes-in]
  (hash-bytes (goog.crypt.Md5.) bytes-in))

(defn md5-bytes
  "convert utf8 string to md5 byte array"
  [string]
(md5- (string->bytes string)))

(defn md5-hex [string]
  "convert utf8 string to md5 hex string"
  (bytes->hex (md5-bytes string)))
