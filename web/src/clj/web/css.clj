(ns web.css
  (:require [garden.def :refer [defstyles]]))

(defstyles footer
  [:footer.page-footer {:padding-top 0
                        :position "absolute"
                        :bottom 0
                        :width "100%"}])

(defstyles navbar
  [:div.navbar-fixed :nav :div.nav-wrapper
    {:text-align "center"}
    [:ul.center {:display "inline-block"}]])

(defstyles home-panel
  [:div.home-panel
   {:margin-top "2rem"}
   [:img.responsive-img {:height "25em"}]
   [:h4.job-title {:margin-top 0}]])

(defstyles contact-panel
  [:div.contact-panel
   [:div.contact-form
    {:margin-top "2.5rem"}]
   [:div.contact-methods
    {:margin-top "2.5rem"}
    [:div.personal-contact
     {:margin-bottom "3rem"}
     [:p {:margin-top 0
          :margin-bottom 0}]]
    [:div.social-contact
     [:a {:color "unset"}]]]])

(defstyles about-panel
  [:div.about-panel
   {:margin-top "2rem"}
   ])

(defstyles experience-panel
  [:div.experience-panel
   {:margin-top "2rem"}
   ])

(defstyles screen
  [:html {:height "100%"}]
  [:body
   {:position "relative"
    :padding-bottom "6rem"
    :min-height "100%"}
   [:p.flow-text {:font-size "1.2rem"}]
   navbar
   footer
   [:div.container
     home-panel
     contact-panel
     about-panel
     experience-panel]])
