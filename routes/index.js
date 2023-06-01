import Express from "express";
import { getUsers, Register, login, Logout } from "../controllers/Users.js";
import { verivyToken } from "../middleware/VerivyToken.js";
import { refreshToken } from "../controllers/RefreshToken.js";

const router = Express.Router();

router.get('/users', verivyToken, getUsers);
router.post('/users', Register);
router.post('/login', login);
router.get('/token', refreshToken);
router.delete('/logout', Logout)


export default router;