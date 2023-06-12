import FlexBetween from "components/flexBetween";
import React, { useState } from "react";
import { Outlet } from "react-router-dom";
import { Box, useMediaQuery } from "@mui/material";
import Navbar from "components/Navbar";

const Layout = () => {
  return (
    <Box width="100%" height="100%">
      <Box>
        <Navbar />
        <Outlet />
      </Box>
    </Box>
  );
};

export default Layout;
