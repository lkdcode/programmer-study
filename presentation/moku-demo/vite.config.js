import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import fs from "fs";
import path from "path";
import { fileURLToPath } from "url";

// ESM 환경에서 __dirname 대신 사용할 변수들
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

export default defineConfig({
  define: {
    global: "window", // 👈 global을 window로 대체
  },
  plugins: [react()],
  server: {
    https: {
      key: fs.readFileSync(
        path.resolve(__dirname, "certs", "dev-lkdcode.moku.run+3-key.pem")
      ),
      cert: fs.readFileSync(
        path.resolve(__dirname, "certs", "dev-lkdcode.moku.run+3.pem")
      ),
    },

    host: "0.0.0.0",
    port: 5173,
    allowedHosts: ["dev-lkdcode.moku.run"],
    proxy: {
      "/api": {
        target: "https://dev-lkdcode.moku.run:18080",
        changeOrigin: true,
        secure: false,
      },
    },
  },
});
