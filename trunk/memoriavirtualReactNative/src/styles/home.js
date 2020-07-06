import {
  heightPercentageToDP as hp,
  widthPercentageToDP as wp,
} from 'react-native-responsive-screen';

const homeStyles = () => {
  return ({
    mainContainer: {
      flex: 1,
      flexDirection: 'column',
      justifyContent: 'flex-start',
      alignItems: 'center',
      paddingHorizontal: wp('2%'),
      paddingVertical: hp('10%'),
      backgroundColor: '#FFFFFF',
    },
    logoImage: {
      width: wp('90%'),
      height: hp('20%'),
      resizeMode: 'contain',
    },
    searchInputContainer: {
      width: wp('90%'),
      height: hp('7.5%'),
      paddingHorizontal: wp('4%'),
      borderWidth: 1,
      borderColor: 'black',
      borderRadius: 20,
    },
    searchInput: {
      width: wp('82%'),
      height: hp('7.5%'),
    },
    footnoteText: {
      fontSize: 14,
      marginTop: hp('26%'),
      textAlign: 'center',
    },
  });
};

export default homeStyles;
