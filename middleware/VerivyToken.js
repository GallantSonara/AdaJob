import Jwt from "jsonwebtoken";

export const verivyToken = (req, res, next) => {
    console.log("verivyToken")
    const authHeader = req.headers['authorization'];
    console.log(authHeader)
    console.log(req.email)
    const token = authHeader && authHeader.split(' ')[1];
    if(token == null) return res.sendStatus(401);
    Jwt.verify(token, process.env.ACCES_TOKEN_SECRET, (err, decoded) => {
        if(err) return res.sendStatus(403);
        req.email = decoded.email;
        next();
    })
}