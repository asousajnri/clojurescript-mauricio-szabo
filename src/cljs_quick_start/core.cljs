(ns cljs-quick-start.core
  (:require ["express" :as express]))

(def app (express))
(def port 3000)

(defn- send-result [res data]
  (. res send (:body data)))

(defn handle-root-2 [req]
  {:body "Hello, World two!"})

(defn- register-get [app handler]
  (. app get "/" (fn [req res]
                   (send-result res (handler req)))))

(defn register-handlers []
  (register-get app handle-root-2)
  (. app listen port (fn []
                       (println "Example app listenning on port" port))))

(defn ^:dev/after-load main []
  (def server (register-handlers))

 (defn ^:dev/before-load stop-server []
   (. server close)
   (println "Stopped server")))
