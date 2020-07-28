import {
  heightPercentageToDP as hp,
  widthPercentageToDP as wp,
} from 'react-native-responsive-screen';

const generalinfoStyles = () => {
  return ({
    container:{
      flex: 1,
      justifyContent: 'flex-start',
      alignItems: 'center',
      backgroundColor: '#fff',

    },
    title:{
      top: wp('5%'),
      backgroundColor: "#b4b4b460",
      width: wp('90%'),
      height: hp('10%'),
      alignItems: 'center',
      justifyContent: 'center',
    },
    inputs:{
      flexDirection:'row',
      alignItems:'center',
      width: wp('90%'),
      marginTop: wp('3%'),
      justifyContent:'space-between',
    },
    spacer:{
      height: wp('9%'),
      borderLeftWidth: 1,
      borderLeftColor: '#262626',
    },
    textInput:{
      height: wp('10%'),
    },
    formTitle:{
      height: wp('10%'),
      width: wp('60%'),
    },
    lastFormTitle:{
      height: wp('15%'),
      width: wp('60%'),
      borderBottomColor: '#00000020',
      borderBottomWidth: 2,
    },

    formInput:{
      flexDirection:'row',
      height: wp('10%'),
      width: wp('25%'),
    },
    form:{
      marginTop:30,
      flexDirection: 'column',
      justifyContent:'flex-start',
      alignItems: 'flex-start',
      width:wp('90%'),
    },
    verticalBar: {
      width: wp('100%'),
    },
    footnoteText: {
      fontSize: 14,
      marginTop: hp('40%'),
      textAlign: 'center',
    },
  });
};

export default generalinfoStyles;
