import Sequelize from 'sequelize'
import db from '../config/database.js'

const { DataTypes } = Sequelize

// Define the JobListing model
const JobListing = db.define('task', {
  task_id: {
    type: DataTypes.INTEGER,
    primaryKey: true,
    autoIncrement: true,
  },
  task_title: {
    type: DataTypes.STRING,
  },
  task_type: {
    type: DataTypes.STRING,
  },
  reward_type: {
    type: DataTypes.STRING,
  },
  reward: {
    type: DataTypes.STRING,
  },
  deadline: {
    type: DataTypes.DATE,
  },
  task_link: {
    type: DataTypes.STRING,
  },
  description: {
    type: DataTypes.TEXT,
  },
  enrollment: {
    type: DataTypes.BOOLEAN,
  },
},{
  timestamps: false,
});

// Route to fetch all data from the joblisting table
export default JobListing