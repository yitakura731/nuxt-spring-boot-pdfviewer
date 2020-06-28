const pkg = require('./package');
require('dotenv').config();

module.exports = {
  mode: 'spa',

  router: {
    linkActiveClass: 'active'
  },

  /*
   ** Headers of the page
   */
  head: {
    title: 'My demo app',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: pkg.description }
    ],
    link: [{ rel: 'icon', type: 'image/x-icon', href: 'favicon.ico' }]
  },

  /*
   ** Plugins to load before mounting the App
   */
  plugins: ['~/plugins/bootstrap-vue.js', '~/plugins/pdf.js'],

  /*
   ** fontawesome
   */
  fontawesome: {
    imports: [
      {
        set: '@fortawesome/free-solid-svg-icons',
        icons: ['fas']
      },
      {
        set: '@fortawesome/free-regular-svg-icons',
        icons: ['far']
      },
      {
        set: '@fortawesome/free-brands-svg-icons',
        icons: ['fab']
      }
    ]
  },

  modules: ['bootstrap-vue/nuxt', '@nuxtjs/axios', 'nuxt-fontawesome'],

  axios: {
    proxy: true
  },

  proxy: {
    '/api': {
      target: process.env.SERVER_URL || 'http://localhost:8080',
      secure: false
    }
  },

  build: {
    extend(config, ctx) {
      if (ctx.isDev && ctx.isClient) {
        config.devtool = 'eval-source-map';
        config.module.rules.push({
          enforce: 'pre',
          test: /\.(js|vue)$/,
          loader: ['eslint-loader'],
          exclude: /(node_modules)/
        });
      }
    }
  }
};
