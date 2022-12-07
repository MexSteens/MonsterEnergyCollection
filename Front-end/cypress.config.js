const { defineConfig } = require("cypress");

module.exports = defineConfig({
  viewportWidth: 390,
  viewportHeight: 844,
  e2e: {
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
});
