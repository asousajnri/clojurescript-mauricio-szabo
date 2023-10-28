(ns cljs-quick-start.core
  (:require ["express" :as express]))

(def app (express))
(def port 3000)

(defn handle-root [req res]
  (. res send "Hello, the whole world!"))

(defn register-handlers []
  (. app get "/"
    (fn [req res] (handle-root req res)))

  (. app listen port (fn []
                       (println "Example app listenning on port" port))))

(defn ^:dev/after-load main []
  (def server (register-handlers))

 (defn ^:dev/before-load stop-server []
   (. server close)
   (println "Stopped server")))
