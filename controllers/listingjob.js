import JobListing from "../models/joblistings.js"
export const getJobListings = async(req, res) => {
    try {
        const jobListings = await JobListing.findAll();
        res.json(jobListings);
          } catch (error) {
            console.error('Error retrieving job listings:', error);
            res.status(500).json({ error: 'Failed to fetch data' });
          }
    };

// Route to fetch data by ID from the joblisting table
export const getjobbyid= async(req, res) => {
    const { id } = req.params;
  
    try {
      const jobListing = await JobListing.findByPk(id);
      if (!jobListing) {
        res.status(404).json({ error: 'Job listing not found' });
        return;
      }
      res.json(jobListing);
    } catch (error) {
      console.error('Error retrieving job listing:', error);
      res.status(500).json({ error: 'Failed to fetch data' });
    };
};