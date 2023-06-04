import { Sequelize } from "sequelize";

const db = new Sequelize('auth_db','root','123asd',{
    host:"34.101.131.51",
    dialect:"mysql"
});

export default db;
