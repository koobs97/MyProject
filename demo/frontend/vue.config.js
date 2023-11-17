const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  outputDir: '../src/main/java', // Build Directory
	devServer: {
		proxy: 'http://localhost:8081' // Spring Boot Server
	}
})