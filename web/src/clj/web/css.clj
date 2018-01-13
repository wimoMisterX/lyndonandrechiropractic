(ns web.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:img.profile-picture-home {:height "500px"}]
  [:div.wrapper {:min-height "100%"}]
  [:div#main {:padding-bottom "50px"}]
  [:.personal-contact :p:first-of-type {:margin-top 0}]
  [:footer.page-footer {:padding-top 0 :position "fixed" :bottom 0 :width "100%"}])
