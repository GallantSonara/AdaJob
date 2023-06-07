import express from "express";
import dotenv from "dotenv";
import cookieParser from "cookie-parser";
import cors from "cors";
import db from "./config/database.js";
import router from "./routes/index.js";
dotenv.config();
const app = express();

try {
    await db.authenticate();
    console.log('Database Connected...');
} catch (error) {
    console.error(error)
}

app.use(cors({ credentials:true, origin:'http:/localhost:3000'}));
app.use(cookieParser());
app.use(express.json());
app.use(router);

app.listen(8080, () => {
  console.log('listening on port', 8080);
});



app.get('/', (req, res) => {
  res.send('Hello World!')
})