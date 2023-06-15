import React from "react";
import {
  Box,
  Divider,
  Drawer,
  IconButton,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Typography,
  useTheme,
} from "@mui/material";
import {
  ChevronRightOutlined,
  ShoppingCartOutlined,
  ReceiptLongOutlined,
  PublicOutlined,
  PointOfSaleOutlined,
  TodayOutlined,
  CalendarMonthOutlined,
  AdminPanelSettingsOutlined,
  TrendingUpOutlined,
  PieChartOutlined,
  HomeOutlined,
  SettingsOutlined,
  Groups2Outlined,
} from "@mui/icons-material";
import HouseOutlinedIcon from "@mui/icons-material/HouseOutlined";
import PaidOutlinedIcon from "@mui/icons-material/PaidOutlined";
import ChevronLeft from "@mui/icons-material/ChevronLeft";
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import FlexBetween from "./flexBetween";
import profileImage from "assets/profile.jpeg";
import { boxSizing } from "@mui/system";
import StorageOutlinedIcon from "@mui/icons-material/StorageOutlined";
import SpeedOutlinedIcon from "@mui/icons-material/SpeedOutlined";

// TODO: put in a class
function toCamelCase(str) {
  // Split string on space character, convert to array
  let words = str.split(" ");

  // Transform words to camel case
  words = words.map((word, index) => {
    // Lowercase for the first word, capitalize others
    if (index === 0) {
      return word.toLowerCase();
    }
    return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
  });

  // Join words back together without spaces
  return words.join("");
}

const navItems = [
  {
    text: "Dashboard",
    icon: <HomeOutlined />,
  },
  {
    text: "Client Facing",
    icon: null,
  },
  {
    text: "Apartments",
    icon: <HouseOutlinedIcon />,
  },
  {
    text: "Reservations",
    icon: <ShoppingCartOutlined />,
  },
  {
    text: "Customers",
    icon: <Groups2Outlined />,
  },
  {
    text: "Transactions",
    icon: <ReceiptLongOutlined />,
  },
  {
    text: "Calendar",
    icon: <CalendarMonthOutlined />,
  },
  {
    text: "Management",
    icon: null,
  },
  {
    text: "Profits",
    icon: <PaidOutlinedIcon />,
  },
  {
    text: "Stats",
    icon: <PieChartOutlined />,
  },
  {
    text: "Geography",
    icon: <PublicOutlined />,
  },
  {
    text: "Admin",
    icon: <AdminPanelSettingsOutlined />,
  },
  {
    text: "Performance",
    icon: <TrendingUpOutlined />,
  },
  {
    text: "Database",
    icon: null,
  },
  {
    text: "Data Generation",
    icon: <StorageOutlinedIcon />,
  },
  {
    text: "Speed Test",
    icon: <SpeedOutlinedIcon />,
  },
];

const Sidebar = ({
  drawerWidth,
  isSidebarOpen,
  setIsSidebarOpen,
  isNonMobile,
}) => {
  const { pathname } = useLocation();
  const [active, setActive] = useState("");
  const navigate = useNavigate();
  const theme = useTheme();

  useEffect(() => {
    setActive(pathname.substring(1));
  }, [pathname]);

  return (
    <Box component="nav">
      {isSidebarOpen && (
        <Drawer
          open={isSidebarOpen}
          onClose={() => setIsSidebarOpen(false)}
          variant="persistent"
          anchor="left"
          sx={{
            width: drawerWidth,
            "& .MuiDrawer-paper": {
              color: theme.palette.secondary[200],
              backgroundColor: theme.palette.background.alt,
              boxSizing: "border-box",
              borderWidth: isNonMobile ? 0 : "2px",
              width: drawerWidth,
            },
          }}
        >
          <Box width="100%">
            <Box m="1.5rem 2rem 2rem 3rem">
              <FlexBetween color={theme.palette.secondary.main}>
                <Box display="flex" alignItems="center" gap="0.5rem">
                  <Typography variant="h4" fontWeight="bold">
                    Welcome!
                  </Typography>
                </Box>
                {!isNonMobile && (
                  <IconButton onClick={() => setIsSidebarOpen(!isSidebarOpen)}>
                    <ChevronLeft />
                  </IconButton>
                )}
              </FlexBetween>
            </Box>
            <List>
              {navItems.map(({ text, icon }) => {
                if (!icon) {
                  return (
                    <Typography key={text} sx={{ m: "2.25rem 0 1rem 3rem" }}>
                      {text}
                    </Typography>
                  );
                }
                const ccText = toCamelCase(text);
                return (
                  <ListItem key={text} disablePadding>
                    <ListItemButton
                      onClick={() => {
                        navigate(`/${ccText}`);
                        setActive(ccText);
                      }}
                      sx={{
                        backgroundColor:
                          active === ccText
                            ? theme.palette.secondary[300]
                            : "transparent",
                        color:
                          active === ccText
                            ? theme.palette.primary[600]
                            : theme.palette.secondary[100],
                      }}
                    >
                      <ListItemIcon
                        sx={{
                          ml: "2rem",
                          color:
                            active === ccText
                              ? theme.palette.primary[600]
                              : theme.palette.secondary[200],
                        }}
                      >
                        {icon}
                      </ListItemIcon>
                      <ListItemText primary={text} />
                      {active === ccText && (
                        <ChevronRightOutlined sx={{ ml: "auto" }} />
                      )}
                    </ListItemButton>
                  </ListItem>
                );
              })}
            </List>
          </Box>
        </Drawer>
      )}
    </Box>
  );
};

export default Sidebar;