(ns web.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:html {:height "100%"}]
  [:body {:position "relative" :padding-bottom "6rem" :min-height "100%"}]
  [:img.profile-picture-home {:height "500px"}]
  [:div.personal-contact :p:first-of-type {:margin-top 0}]
  [:footer.page-footer {:padding-top 0 :position "absolute" :bottom 0 :width "100%"}])
