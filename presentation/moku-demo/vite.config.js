import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vite.dev/config/
export default defineConfig({
  define: {
    global: "window", // 👈 global을 window로 대체
  },
  plugins: [react()],
  server: {
    host: "0.0.0.0",
    port: 5173,
  },
});
