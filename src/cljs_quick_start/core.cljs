(ns cljs-quick-start.core
  (:require react-dom))

(.render js/ReactDOM
         (.createElement js/React "h2" nil "Hello, Hello!")
         (.getElementById js/document "app"))
