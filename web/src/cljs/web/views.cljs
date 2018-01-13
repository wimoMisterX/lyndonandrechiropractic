(ns web.views
  (:require [re-frame.core :as re-frame]
            [web.subs :as subs]
            ))


;; home

(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div (str "Hello from " @name ". This is the Home Page.")
     [:div [:a {:href "#/about"} "go to About Page"]]]))


;; about

(defn about-panel []
  [:div "This is the About Page."
   [:div [:a {:href "#/"} "go to Home Page"]]])


;; experience

(defn experience-panel []
  [:div "This is the experience Page."
   [:div [:a {:href "#/"} "go to Home Page"]]])


;; contact

(defn contact-panel []
  [:div "This is the contact Page."
   [:div [:a {:href "#/"} "go to Home Page"]]])


;; navigation

(defn nav-panel []
  [:div {:class "navbar-fixed"}
   [:nav
    [:div {:class "nav-wrapper"}
     [:ul {:class "center hide-on-med-and-down"}
      [:li [:a {:href "#/"} "Home"]]
      [:li [:a {:href "#/about"} "About"]]
      [:li [:a {:href "#/experience"} "Experience"]]
      [:li [:a {:href "#/contact"} "Contact"]]]]]])


;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    :experience-panel [experience-panel]
    :contact-panel [contact-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [:div
     (nav-panel)
     [show-panel @active-panel]]))
