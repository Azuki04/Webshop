import {useLocation, useNavigate, useParams, useSearchParams,} from "react-router-dom";
// for the navigation
export const withRouter = (Component) => {
  function ComponentWithRouterProp(props) {
    const location = useLocation();
    let navigate = useNavigate();
    let params = useParams();
    const searchParams = useSearchParams()
    return <Component {...props} router={{ location, navigate, params, searchParams }} />;
  }
  return ComponentWithRouterProp;
};
